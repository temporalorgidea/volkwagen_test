package com.volkswagen.application.service;

import com.volkswagen.application.port.in.CleaningRobotService;
import com.volkswagen.application.port.in.RobotConfiguration;
import com.volkswagen.application.port.in.RobotsDataCommand;
import com.volkswagen.application.port.out.RobotsResultPort;
import com.volkswagen.domain.Robot;
import com.volkswagen.domain.Workplace;

import java.util.ArrayList;
import java.util.List;

import static com.volkswagen.domain.Robot.from;

public class CleaningRobotServiceImpl implements CleaningRobotService {

    private final RobotsResultPort robotsResultPort;

    public CleaningRobotServiceImpl(RobotsResultPort robotsResultPort) {
        this.robotsResultPort = robotsResultPort;
    }

    @Override
    public List<Robot> controlRobots(RobotsDataCommand robotsDataCommand) {

        Workplace workplace = robotsDataCommand.workplace();
        List<Robot> listaRobots = new ArrayList<>();

        /* TODO: The workplace data structure contains all the information necessary to process all robot movements.

            For a successful implementation, processing the information stored in this data structure is imperative.

            If you look at the workplace data structure, you can see the information to create the robot in an initial position.
            There is also information on all the movements that the robot will perform.

            Therefore, to achieve a successful implementation that passes the tests, it is essential to create each robot
            and invoke each of the commands on the corresponding robot.  Finally, the list with the robots that have finished
            all their movements is passed to the robotsResultPort component.
        */
        for (var configuration: robotsDataCommand.configurations()) {
            var robot = Robot.from(workplace, configuration.robotPosition());
            listaRobots.add(robot);

            for(var command: configuration.commands()) {
                robot.executeCommand(command);
            }
            workplace.addFinishedRobot(robot);
        }

        robotsResultPort.processRobotsResult(listaRobots);
        return listaRobots;
    }
}
