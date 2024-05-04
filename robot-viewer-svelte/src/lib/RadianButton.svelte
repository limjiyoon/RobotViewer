<script lang="ts">
    function toggleRadian(event: MouseEvent) {
        const radians = event.target as HTMLDivElement;
        const isRadian = radians.classList.contains('checked');
        if (isRadian) {
            radians.classList.remove('checked');
            radians.textContent = 'Use Radians';
        } else {
            radians.classList.add('checked');
            radians.textContent = 'Use Degrees';
        }


    }

</script>

<button class="toggle" id="radians-toggle" on:click={toggleRadian}>Use Radians</button>
