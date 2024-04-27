<script lang="ts">
    import * as THREE from 'three';
    import {DirectionalLight, LoadingManager, MathUtils, Mesh, WebGLRenderer} from 'three';
    import URDFLoader from 'urdf-loader';
    import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';

    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    // renderer.setSize( window.innerWidth, window.innerHeight );
    const renderer = new WebGLRenderer({ antialias: true })!!;
    // renderer.outputEncoding = THREE.sRGBEncoding;
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

    const ground = new Mesh(new THREE.PlaneGeometry(), new THREE.ShadowMaterial({ opacity: 0.75 }));
    ground.rotation.x = -Math.PI / 2;
    ground.scale.setScalar(30);
    scene.add(ground);

    camera.position.y = 20;
    const controls = new OrbitControls(camera, renderer.domElement);
    controls.minDistance = 4;
    controls.target.y = 1;
    controls.update();

    const manager = new LoadingManager();
    const loader = new URDFLoader( manager );
    loader.load(
        '/src/assets/T12/urdf/T12.urdf',
        (robot) => {
            robot.rotation.x = -Math.PI / 2;
            scene.add(robot);
        }
    )

    const render = () => {
        requestAnimationFrame(render);
        renderer.render(scene, camera);
    }

    render();
</script>

