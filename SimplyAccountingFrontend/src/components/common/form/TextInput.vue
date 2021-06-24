<template>
  <v-text-field
      :id="fullTagName"
      :name="fullTagName"
      v-model.trim="inputValue"
      @change="emptyToNull"
      :counter="counter"
      :error-messages="errors"
      :label="label"
      :type="type"
      :readonly="disabled"
      :clearable="clearable && !disabled"
      :hide-details="hideDetails"/>
</template>

<script>
import {emptyToNull} from "@/core/service/FormatterService";

export default {
  name: "TextInput",
  props: {
    value: {
      type: String
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
    counter: {
      type: Number,
      default: undefined
    },
    disabled: {
      type: Boolean,
      default: false
    },
    clearable: {
      type: Boolean,
      default: false
    },
    type: {
      type: String,
      default: 'text'
    },
    hideDetails: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      inputValue: this.value,
      tagPrefix: 'input'
    }
  },
  methods: {
    emptyToNull() {
      this.inputValue = emptyToNull(this.inputValue);
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