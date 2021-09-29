<template>
  <v-btn class="delete-button" @click="emitClick"
         icon
         small
         :disabled="disabled">
    <v-icon>
      mdi-delete
    </v-icon>
  </v-btn>
</template>

<script>
export default {
  name: "DeleteButton",
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