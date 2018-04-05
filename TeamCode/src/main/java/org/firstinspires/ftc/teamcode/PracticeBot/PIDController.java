package org.firstinspires.ftc.teamcode.PracticeBot;

/**
 * Created by ACME Robotics on 3/27/2018.
 */

public class PIDController {

    public double p, i, d = 0;
    public double sum;
    public double maxSum = 20;
    private double time;
    private double pastError;
    private boolean firstRun = true;

    public PIDController(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;

    }

    public double update(double error) {
        if (firstRun) {
            time = System.nanoTime() / 1e9;
            pastError = error;
            sum = 0;
            firstRun = false;
            return error * p;
        }
        double now = System.nanoTime() / 1e9;
        double dt = now - time;
        time = now;

        sum += error * dt;
        if (Math.abs(sum) > maxSum) {
            sum = maxSum * Math.signum(sum);
        }
        double slope = (error - pastError) / dt;
        pastError = error;

        return error * p + sum * i + slope * d;

    }
}
