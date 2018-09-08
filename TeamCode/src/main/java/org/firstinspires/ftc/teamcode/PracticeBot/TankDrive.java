package org.firstinspires.ftc.teamcode.PracticeBot;

/**
 * Created by Emma on 3/25/2018.
 */
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="TankDrive", group="TankDrive")
public class TankDrive extends OpMode {

    private Drive drive;
    private Lift lift;

    private StickyGamepad gamepad;

    private Coefficients active = Coefficients.P;
    private boolean disable = true;
    private double increment = .1;

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
        drive = new Drive (hardwareMap);
        lift = new Lift(hardwareMap, telemetry);
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
            lift.pidController.sum = 0;
        }


        if (gamepad.b){
            disable = !disable;
        }
        telemetry.addData("active", active.name());
        telemetry.addData("increment", increment);
        telemetry.addData("sum", lift.pidController.sum);
        telemetry.addData("disable", disable);

        for (Coefficients value: Coefficients.values()) {
            telemetry.addData(value.name(), value.value);
        }

        lift.pidController.p = Coefficients.P.value;
        lift.pidController.i = Coefficients.I.value;
        lift.pidController.d = Coefficients.D.value;

        double forward = -gamepad1.left_stick_y;
        double turn = -gamepad1.right_stick_x;

        drive.setSpeed(forward, turn);

        if (gamepad.left_bumper) {
            lift.up();
        }

        if (gamepad.right_bumper) {
            lift.down();
        }
        lift.update();


    }



    public void stop() {
        drive.setSpeed(0, 0);

    }
}
