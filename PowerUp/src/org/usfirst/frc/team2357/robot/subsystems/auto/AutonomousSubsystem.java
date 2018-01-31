package org.usfirst.frc.team2357.robot.subsystems.auto;

import org.usfirst.frc.team2357.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This subsystem is used to select an {@link AutonomousMode} and a sprint
 * option. Also, the {@link Robot} uses this subsystem to run and stop the
 * {@link AutonomousMode}.
 */
public class AutonomousSubsystem extends Subsystem {
	private final AutonomousChooser autonomousChooser = new AutonomousModeDashboardChooser();
	private AutonomousMode startedMode;
	private PlatformSide switchSide = PlatformSide.UNKNOWN;
	private PlatformSide scaleSide = PlatformSide.UNKNOWN;

	/**
	 * Initializes the subsystem.
	 */
	public AutonomousSubsystem() {
		super();
	}

	/**
	 * Should be called once from {@link Robot#autonomousInit()}.
	 */
	public void autonomousInit() {
		// For safety during testing.
		stop();

		processGameData();

		this.startedMode = this.autonomousChooser.getAutonomousMode();
		this.startedMode.getAutonomousCommand().start();
	}

	/**
	 * Reads the game specific data and processes it into the platform sides.
	 */
	private void processGameData() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		if ((gameData != null) && (gameData.length() > 1)) {
			this.switchSide = gameData.charAt(0) == 'R' ? PlatformSide.RIGHT : PlatformSide.LEFT;
			this.scaleSide = gameData.charAt(1) == 'R' ? PlatformSide.RIGHT : PlatformSide.LEFT;
		}
	}

	/**
	 * Should be called once from {@link Robot#teleopInit()}.
	 */
	public void teleopInit() {
		stop();
	}

	/**
	 * Should be called once from {@link Robot#disabledInit()}.
	 */
	public void disabledInit() {
		stop();
	}

	/**
	 * Stops any started {@link AutonomousMode} and sets the started mode to null.
	 */
	public void stop() {
		if (this.startedMode != null) {
			this.startedMode.getAutonomousCommand().cancel();
			this.startedMode = null;
		}
	}

	/**
	 * There is no default command for this subsystem.
	 */
	public void initDefaultCommand() {
	}

	/**
	 * @return the {@link PlatformSide} for our switch platform from the point of
	 *         view of the drive team.
	 */
	public PlatformSide getSwitchSide() {
		return this.switchSide;
	}

	/**
	 * @return the {@link PlatformSide} for our scale platform from the point of
	 *         view of the drive team.
	 */
	public PlatformSide getScaleSide() {
		return this.scaleSide;
	}
}
