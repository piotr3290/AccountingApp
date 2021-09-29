<template>
  <v-list-item @click="goToDestination" class="flex-min rounded">
    <v-list-item-icon
        v-if="$vssWidth > 1264 || $vssWidth <= 1000"
        class="mx-0 mr-lg-4 justify-center">
      <v-icon>{{ icon }}</v-icon>
    </v-list-item-icon>
    <v-list-item-title v-if="$vssWidth	> 1000">
      {{ text }}
    </v-list-item-title>
  </v-list-item>

</template>

<script>
export default {
  name: "RouterLinkItem",
  props: {
    routeName: {
      type: String
    },
    text: {
      type: String
    },
    icon: {
      type: String,
      default: null
    }
  },
  methods: {
    goToDestination() {
      if (this.$route.name !== this.routeName) {
        this.$router.push({name: this.routeName});
      }
    }
  }
}
</script>

<style scoped lang="scss">

.router-link-item {
  text-decoration: none;
}

.v-list-item:hover::before, .v-list-item:focus::before {
  border-radius: 4px !important;
}

</style>