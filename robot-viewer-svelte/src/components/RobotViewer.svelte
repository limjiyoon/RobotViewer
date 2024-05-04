<script lang="ts">
    import * as THREE from 'three';
    import {DirectionalLight, LoaderUtils, LoadingManager, Mesh, WebGLRenderer} from 'three';
    import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';
    import RadianButton from "./RadianButton.svelte";
    import URDFLoader, {type URDFRobot} from "urdf-loader";
    import DrawRobot from "./DrawRobot.svelte";

    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    const renderer = new WebGLRenderer({ antialias: true })!!;
    renderer.shadowMap.enabled = true;
    renderer.shadowMap.type = THREE.PCFSoftShadowMap;
    document.body.appendChild(renderer.domElement);
    document.body.appendChild( renderer.domElement );

    const directionalLight = new DirectionalLight(0xffffff, 1.0);
    directionalLight.castShadow = true;
    directionalLight.shadow.mapSize.setScalar(1024);
    directionalLight.position.set(5, 30, 5);
    scene.add(directionalLight);

    const ambientLight = new THREE.AmbientLight( 0xffffff ); // soft white light
    scene.add( ambientLight );

    const size = 10;
    const divisions = 10;
    const gridHelper = new THREE.GridHelper( size, divisions );
    scene.add(gridHelper)

    const ground = new Mesh(new THREE.PlaneGeometry(), new THREE.ShadowMaterial({ opacity: 0.25 }));
    ground.rotation.x = -Math.PI / 2;
    ground.scale.setScalar(30);
    ground.receiveShadow = true;
    scene.add(ground);

    camera.position.y = 20;
    const controls = new OrbitControls(camera, renderer.domElement);
    controls.minDistance = 4;
    controls.target.y = 1;
    controls.update();

    const onResize = () => {

        renderer.setSize(window.innerWidth, window.innerHeight);
        renderer.setPixelRatio(window.devicePixelRatio);

        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();

    };

    const render = () => {
        requestAnimationFrame(render);
        renderer.render(scene, camera);
    }

    render();
    onResize();
    window.addEventListener('resize', onResize);

    function getRobot(robotName: string): Promise<URDFRobot> {
        const manager = new LoadingManager();
        const loader = new URDFLoader( manager );
        const getRobotUrdfURL = `http://localhost:8080/urdfs/${robotName}.urdf`;
        loader.workingPath = LoaderUtils.extractUrlBase( getRobotUrdfURL );

        return fetch(getRobotUrdfURL)
            .then(res => res.text())
            .then(content => loader.parse(content));
    }

    const robotPromise = getRobot("T12");
</script>

<div id="controls">
    <RadianButton />
    <div id="do-animate" class="toggle checked">Animate Joints</div>
</div>
<!--<RobotVisual scene={scene} robotName="T12"/>-->
{#await robotPromise}
    <p>Waiting...</p>
{:then robot}
    <DrawRobot robot={robot} scene={scene}/>
{/await}
