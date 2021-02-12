<template>
  <div>
    <h1>{{ this.$t('hello') }}</h1>
    <b-button @click="changeLocale('en')">klik</b-button>
  </div>
</template>

<script>

export default {
  name: "Home",
  methods: {
    changeLocale(locale) {
      this.$store.commit('changeLocale', locale);
    }
  }
}

</script>

<style scoped>

</style>