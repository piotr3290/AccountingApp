<template>
  <v-select
      class="language-select"
      :value="language"
      :items="languages"
      item-text="display"
      item-value="code"
      hide-details
      @input="changeLanguage"/>
</template>

<script>

export default {
  name: "LanguageSelect",
  data() {
    return {
      languages: [
        {
          display: 'Eng',
          code: 'en'
        },
        {
          display: 'Pol',
          code: 'pl'
        }
      ],
    }
  },
  computed: {
    language() {
      return this.$store.getters.getLocale;
    }
  },
  methods: {
    changeLanguage(value) {
      this.$store.commit('changeLocale', value);
    }
  }
}
</script>

<style scoped>

</style>