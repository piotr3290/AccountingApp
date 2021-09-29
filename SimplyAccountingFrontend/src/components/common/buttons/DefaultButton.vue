<template>
  <v-btn @click="emitClick"
         depressed
         outlined
         :small="small"
         :type="buttonType"
         :disabled="disabled">
    {{ text }}
  </v-btn>
</template>

<script>
export default {
  name: "DefaultButton",
  props: {
    text: {
      default: ''
    },
    buttonType: {
      type: String,
      default: 'button'
    },
    disabled: {
      type: Boolean,
      default: false
    },
    small: {
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