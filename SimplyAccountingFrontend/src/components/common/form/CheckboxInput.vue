<template>
  <v-checkbox
      v-model="inputValue"
      :id="fullTagName"
      :name="fullTagName"
      :error-messages="errors"
      :disabled="disabled"
      :label="label"/>
</template>

<script>
export default {
  name: "CheckboxInput",
  props: {
    value: {
      type: Boolean
    },
    tagName: {
      type: String,
      default: undefined
    },
    label: {
      type: String,
      default: undefined
    },
    errors: {
      type: Array,
      default: () => []
    },
    disabled: {
      type: Boolean,
      default: false
    },
  },
  data() {
    return {
      inputValue: this.value,
      tagPrefix: 'input-checkbox'
    }
  },
  computed: {
    fullTagName() {
      return this.tagName === undefined
          ? this.tagPrefix
          : this.tagPrefix + '-' + this.tagName;
    }
  },
  watch: {
    inputValue() {
      this.$emit('input', this.inputValue);
    },
    value() {
      this.inputValue = this.value;
    }
  }
}
</script>

<style scoped>

</style>