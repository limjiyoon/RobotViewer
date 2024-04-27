<script lang="ts">
    import * as THREE from 'three';
    import {LoadingManager, MathUtils, Mesh} from 'three';
    import URDFLoader from 'urdf-loader';
    import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';

    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera( 75, window.innerWidth / window.innerHeight, 0.1, 1000 );
    const renderer = new THREE.WebGLRenderer();
    renderer.setSize( window.innerWidth, window.innerHeight );
    document.body.appendChild( renderer.domElement );

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

    function animate() {
        requestAnimationFrame(animate);
        controls.update();
        renderer.render(scene, camera);
    }
    animate();

    const manager = new LoadingManager();
    const loader = new URDFLoader( manager );
    loader.load(
        '/src/assets/T12/urdf/T12.urdf',
        (robot) => {
            robot.rotation.x = -Math.PI / 2;
            scene.add(robot);
        }
    )
</script>