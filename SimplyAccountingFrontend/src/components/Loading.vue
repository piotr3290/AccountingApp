<template>
  <div class="loading-screen">

  </div>
</template>

<script>
export default {
  name: "Loading"
}
</script>

<style scoped>
.loading-screen{
  position: fixed;
  bottom: 0;
  left: 0;
  top: 0;
  right: 0;
  background: rgba(51,59,102,0.8);
  z-index: 5000;
}
</style>