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
    <div id="controls">
        <div id="joint-sliders">
            <ul>
            </ul>
        </div>
    </div>

    {#await robotPromise}
        <p>Waiting...</p>
    {:then robot}
        <JointSliders robot={robot}/>
        <canvas>
            <RobotViewer robot={robot}/>
        </canvas>
    {/await}

</main>
