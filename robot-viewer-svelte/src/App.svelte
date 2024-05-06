<script lang="ts">
  import RobotViewer from './components/RobotViewer.svelte'

  import URDFLoader, {type URDFRobot} from "urdf-loader";
  import {LoaderUtils, LoadingManager} from "three";
  import JointSliders from "./components/JointSliders.svelte";
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

<main>
    {#await robotPromise}
        <p></p>
    {:then robot}
    <div id="controls">
        <div id="joint-sliders">
            <JointSliders robot={robot}/>
        </div>
    </div>
        <canvas>
            <RobotViewer robot={robot}/>
        </canvas>
    {/await}
</main>
