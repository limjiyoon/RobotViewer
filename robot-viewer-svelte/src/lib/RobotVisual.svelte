<script lang="ts">
    import * as THREE from "three";
    export let scene: THREE.Scene;
    export let robotName: string;

    import {LoadingManager, MathUtils} from "three";
    import URDFLoader from "urdf-loader";

    const manager = new LoadingManager();
    const loader = new URDFLoader( manager );
    const getRobotUrdfURL = `http://localhost:8080/urdfs/${robotName}.urdf`;
    loader.load(
        getRobotUrdfURL,
        (robot) => {
            robot.rotation.x = -Math.PI / 2;
            robot.traverse(child => {
                child.castShadow = true;
            });
            for (let i = 1; i <= 6; i++) {
                robot.joints[`HP${ i }`].setJointValue(MathUtils.degToRad(30));
                robot.joints[`KP${ i }`].setJointValue(MathUtils.degToRad(120));
                robot.joints[`AP${ i }`].setJointValue(MathUtils.degToRad(-60));
            }
            robot.updateMatrixWorld(true);
            scene.add(robot);
        }
    )
</script>