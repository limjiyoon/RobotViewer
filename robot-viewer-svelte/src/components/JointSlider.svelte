<script lang="ts">
    import type {URDFJoint} from "urdf-loader";
    import {writable} from "svelte/store";

    export let joint: URDFJoint;

    function isTargetJointType(jointType: string) {
        switch (jointType) {
            case 'continuous':
            case 'revolute':
            case 'prismatic':
                return true;
            default:
                return false;
        }
    }

    function getMinJointValue(): number {
        if (joint.jointType === 'continuous') {
            return -6.28;
        }
        else {
            return joint.limit.lower as number;
        }
    }

    function getMaxJointValue(): number {
        if (joint.jointType === 'continuous') {
            return 6.28;
        }
        else {
            return joint.limit.upper as number;
        }
    }

    let jointAngle = writable(joint.angle);
    $: jointAngle.set(joint.angle);

    function updateJoint(event: Event) {
        const newAngle = parseFloat((event.target as HTMLInputElement).value);
        joint.setJointValue(newAngle);
        jointAngle.set(newAngle);
        console.log(newAngle, joint.jointValue[0], joint.angle)
    }
</script>

{#if isTargetJointType(joint.jointType)}
    <li>
        <span title="{joint.name}">{joint.name}</span>
        <input type="range"
               step="0.0001"
               min={getMinJointValue()}
               max={getMaxJointValue()}
               value={$jointAngle}
               on:input={updateJoint}
        />
        <input type="number"
               step="0.0001"
               min={getMinJointValue()}
               max={getMaxJointValue()}
               value={$jointAngle}
               on:input={updateJoint}
        />
    </li>
{/if}