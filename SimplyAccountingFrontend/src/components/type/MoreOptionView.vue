<template>
  <v-card class="more-options-view">
    <v-card-title class="text-h4 justify-center">
      {{ $t('common.configuration') }}
    </v-card-title>

    <v-divider/>

    <v-card-actions class="py-4">
      <v-row>
        <v-col class="text-center">
          <v-btn
              class="mx-4"
              rounded
              outlined
              depressed
              @click="goTo('areaTypeList')">
            {{ $_.capitalize($t('area.type')) }}
          </v-btn>
        </v-col>
        <v-col class="text-center">
          <v-btn
              class="mx-4"
              rounded
              outlined
              depressed
              @click="goTo('premisesTypeList')">
            {{ $_.capitalize($t('premises.type')) }}
          </v-btn>
        </v-col>
      </v-row>

    </v-card-actions>
  </v-card>
</template>

<script>


export default {
  name: "MoreOptionView",
  methods: {
    goTo(pathName) {
      this.$router.push({
        name: pathName
      });
    }
  }
}
</script>

<style scoped>

</style>