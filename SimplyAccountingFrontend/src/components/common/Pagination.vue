<template>
  <v-pagination
      v-model="page"
      :length="length"/>
</template>

<script>
export default {
  name: "Pagination",
  props: {
    value: {},
    length: {
      type: [Number, String]
    }
  },
  data() {
    return {
      page: this.value
    }
  },
  watch: {
    page() {
      this.$emit('input', this.page);
    },
    value() {
      this.page = this.value;
    }
  }
}
</script>

<style scoped>

</style>