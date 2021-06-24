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
          :clearable="clearable && !disabled"/>
    </template>

    <v-date-picker
        v-model="date"
        :show-current="false"
        scrollable
        :readonly="disabled">

      <v-spacer/>

      <v-btn
          text
          @click="menu = false">
        {{ $_.capitalize($t('common.cancel')) }}
      </v-btn>

      <v-btn
          text
          @click="saveDate">
        {{ $_.capitalize($t('common.ok')) }}
      </v-btn>
    </v-date-picker>
  </v-menu>
</template>

<script>
export default {
  name: "DateInput",
  props: {
    value: {
      type: String,
      default: new Date().toISOString().substr(0, 10)
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
    clearable: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      date: this.value,
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
    saveDate() {
      this.$refs.menu.save(this.date);
    }
  },
  watch: {
    date() {
      this.$emit('input', this.date);
    },
    value() {
      this.date = this.value;
    }
  }
}
</script>

<style scoped>

</style>