<template>
  <v-card>
    <h1 class="error text-center">
      {{ $_.upperCase($t('common.notFound')) }}
    </h1>
  </v-card>
</template>

<script>
export default {
  name: "NotFound"
}
</script>

<style scoped>

</style>