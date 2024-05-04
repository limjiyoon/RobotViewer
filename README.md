# URDF Robot Viewer
This project is a web application that allows users to view and interact with URDF (Unified Robot Description Format) robot models. It provides a 3D rendering of the robot using Three.js and enables users to view and modify the joint values of the robot.

## Features

- URDF Robot Rendering: The application can load and display URDF robot models in a 3D environment using Three.js.
- Joint Value Display: Users can view the current values of each joint in the robot model.
- Robot Selection: Users can select and load different URDF robot models from a database.
- Robot Upload: Users can upload new URDF robot models to the database.

## Installation

Clone the repository:
```
git clone https://github.com/limjiyoon/RobotViewer.git
```

Navigate to the project directory:
```
cd robot-viewer-svelte
```

Install the dependencies:
```
bun install
```


## Usage
Start the development server:
```
bun start
```
This will launch the application in your default web browser.


Use the application interface to:
- View the loaded URDF robot model in the 3D viewer.
- Inspect the current values of each joint in the robot.
- Select a different URDF robot model from the database.
- Upload a new URDF robot model to the database.


## Dependencies
The main dependencies used in this project are:

- Three.js: A cross-browser JavaScript library used to create and display 3D computer graphics in a web browser.
- Svelte: A JavaScript library for building user interfaces.
- Spring: A flexible kotlin web application framework.
- MongoDB: A popular NoSQL database for storing URDF robot models.

## Contributing
Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## License
This project is licensed under the MIT License.
