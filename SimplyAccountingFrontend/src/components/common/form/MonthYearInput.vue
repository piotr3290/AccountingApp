<template>
  <v-menu
      ref="menu"
      v-model="menu"
      :close-on-content-click="false"
      :return-value.sync="date"
      transition="scale-transition"
      offset-y
      min-width="auto">

    <template v-slot:activator="{ on, attrs }">
      <v-text-field
          :id="fullTagName"
          :name="fullTagName"
          v-model="date"
          :label="label"
          :error-messages="errors"
          prepend-icon="mdi-calendar"
          readonly
          v-bind="attrs"
          v-on="on"
          :clearable="!disabled && clearable"
          @input="monthYearChange"/>
    </template>

    <v-date-picker
        v-model="date"
        :show-current="false"
        scrollable
        type="month"
        :readonly="disabled">

      <v-spacer/>

      <v-btn
          text
          @click="menu = false">
        {{ $_.capitalize($t('common.cancel')) }}
      </v-btn>

      <v-btn
          text
          @click="saveChane">
        {{ $_.capitalize($t('common.ok')) }}
      </v-btn>
    </v-date-picker>
  </v-menu>
</template>

<script>
import {monthYearDateString} from "@/core/service/DateService";

export default {
  name: "MonthYearInput",
  props: {
    value: {},
    tagName: {
      type: String,
      default: undefined
    },
    month: {
      type: Number,
      default: new Date().getMonth() + 1
    },
    year: {
      type: Number,
      default: new Date().getFullYear()
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
    clearable: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      date: monthYearDateString(this.year, this.month),
      menu: false,
      tagPrefix: 'input'
    }
  },
  computed: {
    fullTagName() {
      return this.tagName === undefined
          ? this.tagPrefix
          : this.tagPrefix + '-' + this.tagName;
    }
  },
  methods: {
    saveChane() {
      this.$refs.menu.save(this.date);
      this.monthYearChange();
    },
    monthYearChange() {
      this.$emit('yearChange', parseInt(this.date?.substr(0, 4)));
      this.$emit('monthChange', parseInt(this.date?.substr(5, 2)));
      this.$emit('input', monthYearDateString(this.year, this.month));
    }
  }

}
</script>

<style scoped>

</style>