<template>
  <v-select
      class="year-select"
      :id="fullTagName"
      :name="fullTagName"
      v-model="selectedYear"
      :error-messages="errors"
      :label="label"
      :items="years"
      :hint="hint"
      :persistent-hint="!isSelected"
      :clearable="clearable && !disabled"
      :readonly="disabled">
    <template #item="{ item }">
      <span class="d-flex justify-center" style="width: 100%;">
        {{ item }}
      </span>
    </template>
  </v-select>
</template>

<script>
export default {
  name: "YearSelect",
  props: {
    value: {
      type: [Number, String]
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
      years: [],
      selectedYear: this.value,
      fullTagName: 'input-year',
    }
  },
  computed: {
    isSelected() {
      return this.selectedYear != null;
    }
  },
  created() {
    for (let i = new Date().getFullYear() + 50; i >= 1990; i--) {
      this.years.push(i);
    }
  },
  watch: {
    selectedYear() {
      this.$emit('input', this.selectedYear);
    }
  }
}
</script>

<style scoped>

</style>