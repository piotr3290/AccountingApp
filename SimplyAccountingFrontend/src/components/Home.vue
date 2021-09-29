<template>
  <v-card class="home">

    <v-card-title class="text-h3">
      <v-icon
          class="mr-4"

          x-large>
        $appIcon
      </v-icon>
      Simply accounting
    </v-card-title>

    <v-divider/>

    <v-card-text>
      <v-row>
        <v-col
            class="text-center"
            v-for="item in mainRoutes"
            :key="item.name">
          <v-btn
              :to="{name: item.name}"
              exact
              rounded
              x-large>
            {{ item.displayText }}
          </v-btn>
        </v-col>
      </v-row>
    </v-card-text>

  </v-card>
</template>

<script>

export default {
  name: "Home",
  computed: {
    mainRoutes() {
      return [
        {
          name: 'cooperatives',
          displayText: this.$_.capitalize(this.$t('cooperative.cooperatives')),
          icon: 'mdi-home-city'
        },
        {
          name: 'accountsPlan',
          displayText: this.$_.capitalize(this.$t('account.plan')),
          icon: 'mdi-chart-tree'
        },
        {
          name: 'rates',
          displayText: this.$_.capitalize(this.$t('rate.rates')),
          icon: 'mdi-poll'
        },
        {
          name: 'landlords',
          displayText: this.$_.capitalize(this.$t('landlord.landlords')),
          icon: 'mdi-account-multiple'
        },
        {
          name: 'contractors',
          displayText: this.$_.capitalize(this.$t('contractor.contractors')),
          icon: 'mdi-account-hard-hat'
        },
        {
          name: 'moreOptions',
          displayText: this.$_.capitalize(this.$t('common.moreOptions')),
          icon: 'mdi-cog'
        }
      ]
    }
  }

}

</script>

<style scoped lang="scss">

</style>