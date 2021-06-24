<template>
  <v-overlay>
    <half-circle-spinner
        :animation-duration="1000"
        :size="80"
        :color="secondary"/>
  </v-overlay>
</template>

<script>
import {HalfCircleSpinner} from 'epic-spinners'
import colors from '@/scss/_colors.scss';

export default {
  name: "Loading",
  components: {HalfCircleSpinner},
  data() {
    return {
      secondary: colors.primaryDark
    }
  }
}
</script>

<style scoped>

</style>