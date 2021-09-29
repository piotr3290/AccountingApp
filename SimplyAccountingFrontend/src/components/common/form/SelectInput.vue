<template>
  <v-select
      :id="fullTagName"
      :name="fullTagName"
      v-model="selectedValue"
      :error-messages="errors"
      :label="label"
      :items="items"
      :item-text="itemTextProperty"
      :item-value="itemValueProperty"
      :hint="hint"
      :persistent-hint="!isSelected"
      :clearable="clearable && !disabled"
      :readonly="disabled"
      @change="emitChange"/>
</template>

<script>
export default {
  name: "SelectInput",
  props: {
    value: {},
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
    items: {
      type: Array,
      default: () => []
    },
    disabled: {
      type: Boolean,
      default: false
    },
    itemTextProperty: {
      type: String,
      default: 'name'
    },
    itemValueProperty: {
      type: String,
      default: 'id'
    },
    hint: {
      type: String,
      default: ''
    },
    clearable: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      tagPrefix: 'input-select',
      selectedValue: this.value
    }
  },
  computed: {
    fullTagName() {
      return this.tagName === undefined
          ? this.tagPrefix
          : this.tagPrefix + '-' + this.tagName;
    },
    isSelected() {
      return this.selectedValue != null;
    }
  },
  methods: {
    emitChange() {
      this.$emit('change');
    }
  },
  watch: {
    selectedValue() {
      this.$emit('input', this.selectedValue);
    },
    value() {
      this.selectedValue = this.value;
    }
  }
}
</script>

<style scoped>

</style>