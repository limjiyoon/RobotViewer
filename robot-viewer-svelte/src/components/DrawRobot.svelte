<script lang="ts">
    import {MathUtils} from "three";
    import * as THREE from "three";
    import type {URDFRobot} from "urdf-loader";

    export let robot: any;
    export let scene: THREE.Scene;

    if (robot) {
        const r = robot as URDFRobot;
        r.rotation.x = -Math.PI / 2;
        r.traverse(child => {
            child.castShadow = true;
        });
        for (let i = 1; i <= 6; i++) {
            r.joints[`HP${i}`].setJointValue(MathUtils.degToRad(30));
            r.joints[`KP${i}`].setJointValue(MathUtils.degToRad(120));
            r.joints[`AP${i}`].setJointValue(MathUtils.degToRad(-60));
        }
        r.updateMatrixWorld(true);
        scene.add(r);
    }
</script>