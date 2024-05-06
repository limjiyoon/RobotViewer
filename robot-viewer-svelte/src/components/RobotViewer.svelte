<script lang="ts">
    import * as THREE from 'three';
    import { LoaderUtils, LoadingManager } from 'three';
    import RadianButton from "./RadianButton.svelte";
    import URDFLoader, {type URDFRobot} from "urdf-loader";
    import DrawRobot from "./DrawRobot.svelte";
    import Background from "./Background.svelte";

    let camera: THREE.PerspectiveCamera;
    let scene: THREE.Scene;
    let renderer: THREE.WebGLRenderer;

    async function getRobot(robotName: string): Promise<URDFRobot> {
        const manager = new LoadingManager();
        const loader = new URDFLoader(manager);
        const getRobotUrdfURL = `http://localhost:8080/urdfs/${robotName}.urdf`;
        loader.workingPath = LoaderUtils.extractUrlBase(getRobotUrdfURL);

        let res = await fetch(getRobotUrdfURL);
        let content: string = await res.text();
        return loader.parse(content);
    }

    const robotPromise = getRobot("T12");
</script>

<div id="controls">
    <RadianButton />
    <div id="joint-controls">
        <ul></ul>
    </div>
    <div id="robot-viewer"></div>
</div>
{#await robotPromise}
    <p>Waiting...</p>
{:then robot}
    <Background scene={scene} camera={camera} renderer={renderer}/>
    <DrawRobot robot={robot} scene={scene}/>
{/await}
