<template>
  <v-btn class="add-button" @click="emitClick"
         fab
         outlined
         color="primary"
         small
         :disabled="disabled">
    <v-icon>
      mdi-plus
    </v-icon>
  </v-btn>
</template>

<script>
export default {
  name: "AddButton",
  props: {
    disabled: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    emitClick() {
      this.$emit('click');
    }
  }
}
</script>

<style scoped>

</style>