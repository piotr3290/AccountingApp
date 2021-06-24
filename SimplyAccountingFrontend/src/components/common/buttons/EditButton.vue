<template>
  <v-btn class="edit-button" @click="emitClick"
         icon
         small
         :disabled="disabled">
    <v-icon>
      mdi-pencil
    </v-icon>
  </v-btn>
</template>

<script>
export default {
  name: "EditButton",
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