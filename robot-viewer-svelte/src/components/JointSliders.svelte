<script lang="ts">
    import type {URDFJoint, URDFRobot} from "urdf-loader";
    import JointSlider from "./JointSlider.svelte";

    export let robot: URDFRobot;

    const sliderList = document.querySelector('#joint-sliders ul') as HTMLUListElement;

    function getSortedJoints(): URDFJoint[] {
        return Object
            .keys(robot.joints)
            .sort((a, b) => {

                const da = a.split(/[^\d]+/g).filter(v => !!v).pop();
                const db = b.split(/[^\d]+/g).filter(v => !!v).pop();

                if (da !== undefined && db !== undefined) {
                    const delta = parseFloat(da) - parseFloat(db);
                    if (delta !== 0) return delta;
                }

                if (a > b) return 1;
                if (b > a) return -1;
                return 0;

            })
            .map(key => robot.joints[key])
    }
    //     .map(key => robot.joints[key])
    //     .forEach(joint => {
    //

    //
    //         sliderList.appendChild(li);
    //
    //         // update the joint display
    //         const slider = li.querySelector('input[type="range"]') as HTMLInputElement;
    //         const input = li.querySelector('input[type="number"]') as HTML
    //         let angle: string;
    //         if (Math.abs(joint.angle as number) > 1) {
    //             angle = joint.angle.toFixed(1);
    //         } else {
    //             angle = joint.angle.toPrecision(2);
    //         }
    //
    //         input.value = parseFloat(angle);
    //         li.update = () => {
    //             const degMultiplier = radiansToggle.classList.contains('checked') ? 1.0 : RAD2DEG;
    //             let angle = joint.angle;
    //
    //             if (joint.jointType === 'revolute' || joint.jointType === 'continuous') {
    //                 angle *= degMultiplier;
    //             }
    //
    //             if (Math.abs(angle) > 1) {
    //                 angle = angle.toFixed(1);
    //             } else {
    //                 angle = angle.toPrecision(2);
    //             }
    //
    //             input.value = parseFloat(angle);
    //
    //             // directly input the value
    //             slider.value = joint.angle;
    //
    //             if (viewer.ignoreLimits || joint.jointType === 'continuous') {
    //                 slider.min = -6.28;
    //                 slider.max = 6.28;
    //
    //                 input.min = -6.28 * degMultiplier;
    //                 input.max = 6.28 * degMultiplier;
    //             } else {
    //                 slider.min = joint.limit.lower;
    //                 slider.max = joint.limit.upper;
    //
    //                 input.min = joint.limit.lower * degMultiplier;
    //                 input.max = joint.limit.upper * degMultiplier;
    //             }
    //         };
    //
    //         switch (joint.jointType) {
    //
    //             case 'continuous':
    //             case 'prismatic':
    //             case 'revolute':
    //                 break;
    //             default:
    //                 li.update = () => {};
    //                 input.remove();
    //                 slider.remove();
    //
    //         }
    //
    //         slider.addEventListener('input', () => {
    //             viewer.setJointValue(joint.name, slider.value);
    //             li.update();
    //         });
    //
    //         input.addEventListener('change', () => {
    //             const degMultiplier = radiansToggle.classList.contains('checked') ? 1.0 : RAD2DEG;
    //             viewer.setJointValue(joint.name, input.value * degMultiplier);
    //             li.update();
    //         });
    //
    //         li.update();
    //
    //         sliders[joint.name] = li;
    //     });
</script>
<ul>
    {#each getSortedJoints() as joint}
        <JointSlider {joint} />

    {/each}
</ul>