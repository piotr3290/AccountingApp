<template>
  <v-text-field
      :id="fullTagName"
      :name="fullTagName"
      type="number"
      v-model.number="inputValue"
      :error-messages="errors"
      :label="label"
      :step="step"
      @change="formatValue"
      :clearable="clearable && ! disabled"
      :readonly="disabled">
    <template v-slot:append>
      <v-icon v-if="!disabled" @click="decrement" medium>mdi-minus</v-icon>
      <v-icon v-if="!disabled" @click="increment" medium>mdi-plus</v-icon>
    </template>
  </v-text-field>
</template>

<script>
export default {
  name: "NumberInput",
  props: {
    value: {
      type: [Number, String]
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
    step: {
      type: [Number, String],
      default: 'any'
    },
    numberFormatter: {
      type: Function,
      default: (value) => value
    },
    disabled: {
      type: Boolean,
      default: false
    },
    clearable: {
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
  computed: {
    fullTagName() {
      return this.tagName === undefined
          ? this.tagPrefix
          : this.tagPrefix + '-' + this.tagName;
    },
    stepValue() {
      return typeof this.step === 'number' && !isNaN(this.step)
          ? this.step
          : 1;
    }
  },
  methods: {
    increment() {
      let numberValue = parseFloat(this.inputValue);
      this.inputValue = (isNaN(numberValue) ? 0.0 : numberValue) + this.stepValue;
    },
    decrement() {
      let numberValue = parseFloat(this.inputValue);
      this.inputValue = (isNaN(numberValue) ? 0.0 : numberValue) - this.stepValue;
    },
    formatValue() {
      this.inputValue = this.numberFormatter(this.inputValue);
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

/deep/ input[type=number]::-webkit-inner-spin-button,
/deep/ input[type=number]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

</style>