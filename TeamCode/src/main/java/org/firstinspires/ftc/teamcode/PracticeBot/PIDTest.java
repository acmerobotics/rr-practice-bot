package org.firstinspires.ftc.teamcode.PracticeBot;

import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by ACME Robotics on 3/29/2018.
 */

@TeleOp(name="pidTest")
public class PIDTest extends OpMode {

    private DcMotor motor;
    private PIDController pidController;
    private StickyGamepad gamepad;
    private double setpoint = 0;
    private double p = 0;
    private double i =0;
    private double increment = .1;
    private Coefficients active = Coefficients.P;
    private boolean disable = true;

    private enum Coefficients {
        P {
            @Override
            Coefficients next() {
                return Coefficients.I;
            }
        },
        I {
            @Override
            Coefficients next() {
                return Coefficients.D;
            }
        },
        D {
            @Override
            Coefficients next() {
                return Coefficients.P;
            }
        };
        double value;
        abstract Coefficients next();
    }

    public void init() {
        motor = hardwareMap.dcMotor.get("motor");
        pidController = new PIDController(.1, 0, 0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setPower(0);
        gamepad = new StickyGamepad(gamepad1);

    }

    public void loop() {
        gamepad.update();

        if (gamepad.dpad_up) {
            active.value += increment;
        }
        if (gamepad.dpad_down) {
            active.value -= increment;
        }

        if (gamepad.dpad_left) {
            increment /= 10;
        }

        if (gamepad.dpad_right) {
            increment *= 10;
        }

        if (gamepad.a){
            active = active.next();
        }

        if (gamepad.y){
            pidController.sum = 0;
        }

        if (gamepad.x) {
            setpoint = motor.getCurrentPosition();
        }

        if (gamepad.b){
            disable = !disable;
        }
        telemetry.addData("active", active.name());
        telemetry.addData("increment", increment);
        telemetry.addData("sum", pidController.sum);
        telemetry.addData("disable", disable);

        for (Coefficients value: Coefficients.values()) {
            telemetry.addData(value.name(), value.value);
        }

        pidController.p = Coefficients.P.value;
        pidController.i = Coefficients.I.value;
        pidController.d = Coefficients.D.value;

        double error = motor.getCurrentPosition() - setpoint;
        telemetry.addData("error", error);

        double control = pidController.update(error);
       // if (Double.isNaN(control)) control = 0;
        control = Range.clip(control, -1, 1);
        telemetry.addData("control", control);

        if (disable){
            motor.setPower(0);
        }
        else {
            motor.setPower(control);
        }

    }
}
