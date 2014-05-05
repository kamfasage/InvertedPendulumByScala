InvertedPendulumByScala
=======================

An Inverted Pendulum Implemented By Scala

Overview
--

This project is mainly forked from https://github.com/raphirm/InversePendulum and re-implemented by Scala. All credit should go to the authors of the orignal project: Roger Bollmann and Raphael Marques.

Thanks to the tool of https://github.com/mysema/scalagen and IntelliJ Idea Scala plugin, I can do the major Scala implementation by automatically cover the Java Code to Scala Code.

My contribution only covers the troubleshooting, debugging and German to English language translation (also thanks to Google Translate). 

Here are the brief summary of design doc translated from the original German docs:

------------------------------------------------

Requirements 
--
We have been awarded the contract to implement an inverted pendulum using software. We need to understand the physical formula and integrate it into our software. The application can be implemented with freely chosen programming language. The pendulum has finally inverse are held by a motor.

Introduction
--
Who has not tried to balance a broom handle as a child as long as possible on the palm ? In order to keep the broomstick upright he had to be constantly monitored and corrected by hand position if it is moved to one side. Although the coordinative ability was a challenge for the people it bearable and controllable. However, for machines that is a very big challenge . Finally, the physics of the inverted pendulum must be taught to the computer for now.

This inverse pendulum is both one of the most important and afterwards a classical problem of the control technology. The mechanical structure consists of a motor which can move on a rail back and forth to a freely movable rod is mounted , has secured a mass at the end . The task of the motor, it is to swing the pendulum in vertical position above the engine and stabilize there. Teach all of these physical laws of a machine is a very challenging task.

Application: This system is also frequently used in practice: 
- Balancing a rocket when driving from the assembly hall to the launch pad 
- Stabilization of the vertical position of a space shuttle in the first flight segments 
- Keep a biped robot in an upright position - a resting man can be considered as an inverted pendulum 
- Dynamics of a robot arm in the event that the application of force is below the center of gravity of the arm and thus the system is unstable 
- Single-axis, self-stabilizing scooters, for example, Segway Human Transporter

Our goal is one such inverse pendulum with Java programming and make illustrative with a GUI. In the GUI the following values can be adjusted and the simulation will take this into account: 
- Pendulum mass 
- Pendulum length 
- Speed of the pendulum 
- Resistance 
- Maximum speed of the motor 
- Position of engine

In addition, we set out to create an extensible system as possible, so that one could very easily continue to perform calculations (eg double inverted pendulum).

Architecture
--
Our application is built roughly the following approach. We tried to achieve consistent pattern (Factory and Strategy Pattern) a loose coupling as possible. Unfortunately, the architecture may have waned somewhat enlarged complexity. Due to the lack of time we could refactor only about 80%. However, we think that the architecture was largely adhered to, and the application has an above average standard.

[png]

In principle, we divided into five components: 
* 1.  GUI: All the user sees is the GUI package. The control frame is the interface to the actual program. The controller (and initially the Factory) is in Control Frame instantiated. 
* 2.  Helper: Is the brain of the application. It instantiates all objects according GUI provision (Factory). The controller initiates for each image the calculations and reactions. 
* 3.  Devices: These are containers for the visible components on the GUI. Special feature: you distinguished himself into the graph field. Otherwise, these classes contain in principle only Getter and setter methods. 
* 4.  Calculation: The physics of the application is shown in the Equations. The calculation of the Physics (3/8 Rule Runge cowl) approximated by the equation solver. 
* 5.  Algorithm: The algorithm determines how the motor should rotate in the last packet included.

Physical Simulation
--
kinematics

