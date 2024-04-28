<script lang="ts">
    import * as THREE from 'three';
    import {DirectionalLight, LoadingManager, MathUtils, Mesh, WebGLRenderer} from 'three';
    import URDFLoader from 'urdf-loader';
    import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';

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

    const manager = new LoadingManager();
    const loader = new URDFLoader( manager );
    loader.load(
        '/src/assets/T12/urdf/T12.urdf',
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

            const box = new THREE.Box3()
            const newBox = box.setFromObject(robot);
            // robot.position.y -= newBox.min.y;
            scene.add(robot);
        }
    )

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
</script>

