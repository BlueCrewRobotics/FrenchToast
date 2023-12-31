package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final CommandXboxController driverController = new CommandXboxController(0);


    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();



    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
//        s_Swerve.setDefaultCommand(
//            new TeleopSwerve(
//                s_Swerve,
//                () -> -driverController.getLeftY(),
//                () -> -driverController.getLeftX(),
//                () -> -driverController.getRightX(),
//                () -> driverController.leftBumper().getAsBoolean()
//            )
//        );

        s_Swerve.setDefaultCommand(
                new TeleopSwerveExperiment(
                        s_Swerve,
                        driverController
                )
        );

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        driverController.y().onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        driverController.x().onTrue(new InstantCommand(s_Swerve::xLockWheels));

        driverController.a().whileTrue(new CmdPrintWheelSpeeds(s_Swerve));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve);
    }
}
