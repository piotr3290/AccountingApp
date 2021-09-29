<template>
  <v-card class="premises-view">

    <template v-if="isPremisesFound">
      <v-card-title class="position-relative">
        <div class="mr-8">
          {{
            $_.capitalize($t('premises.premise')) + ' ' + premises.premisesNumber + ' (' + premises.premisesTypeName + ')'
          }}
          <edit-button
              class="ml-2"
              @click="editPremises"/>
        </div>
        <v-spacer/>

        <v-menu
            left
            bottom>
          <template #activator="{ on, attrs }">
            <v-btn
                class="menu-button mr-2 mt-4 mr-sm-4"
                v-bind="attrs"
                v-on="on"
                :icon="isSmall"
                :outlined="!isSmall">
              <v-icon v-if="isSmall">
                mdi-dots-vertical
              </v-icon>
              <template v-if="!isSmall">
                {{ $_.capitalize($t('common.options')) }}
              </template>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
                v-for="(option, index) in menuOptions"
                :key="index"
                @click="option.onClickFunction">
              <v-list-item-title>
                {{ option.text }}
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-card-title>

      <v-card-subtitle class="pb-0">
        {{ premises.buildingAddress }}
      </v-card-subtitle>

      <v-card-subtitle class="pt-0">
        {{ premises.cooperativeName }}
      </v-card-subtitle>

      <v-divider/>

      <v-card-text class="pt-0">

        <div class="text-h6 ml-1 mb-2 mt-6">
          {{ $_.capitalize($t('consumption.consumptions')) }}
        </div>

        <v-divider/>

        <consumption-list
            :premises-id="premises.id"
            :consumptions="premises.consumptions"/>

        <v-divider/>

        <div class="text-h6 ml-1 mb-2 mt-6">
          {{ $_.capitalize($t('landlord.landlords')) }}
        </div>

        <v-divider/>

        <premises-landlord-list
            :premises-landlords="premises.premisesLandlords"
            :premises-id="premises.id"/>

        <v-divider/>

        <div class="text-h6 ml-1 mb-2 mt-6">
          {{ $_.capitalize($t('area.surfaces')) }}
        </div>

        <v-divider/>

        <area-list
            :areas="premises.areas"
            :premises-id="premises.id"/>

        <v-divider/>

        <div class="text-h6 ml-1 mb-2 mt-6">
          {{ $_.capitalize($t('peopleNumber.numbers')) }}
        </div>

        <v-divider/>

        <people-number-list
            :people-numbers="premises.peopleNumbers"
            :premises-id="premises.id"/>

        <v-divider/>

      </v-card-text>
    </template>

    <v-card-title v-else>
      {{ $t('premises.messages.notFound') }}
    </v-card-title>

  </v-card>
</template>

<script>
import PremisesService from "@/core/service/PremisesService";
import AreaList from "@/components/area/AreaList";
import PeopleNumberList from "@/components/peopleNumber/PeopleNumberList";
import PremisesLandlordList from "@/components/premisesLandlord/PremisesLandlordList";
import ConsumptionList from "@/components/consumption/ConsumptionList";
import EditButton from "@/components/common/buttons/EditButton";

export default {
  name: "PremisesView",
  components: {EditButton, ConsumptionList, PremisesLandlordList, PeopleNumberList, AreaList},
  props: ['id'],
  data() {
    return {
      premises: {
        id: null,
        premisesNumber: null,
        premisesTypeName: null,
        buildingId: null,
        buildingAddress: null,
        cooperativeName: null,
        areas: [],
        peopleNumbers: [],
        premisesLandlords: [],
        consumptions: []
      }
    }
  },
  methods: {
    loadPremises() {
      this.$loadingPromises([this.loadPremisesRequest()]);
    },
    loadPremisesRequest() {
      return PremisesService.getPremisesView(this.id)
          .then(response => {
            this.premises = response.data;
          })
          .catch(error => {
            this.premises.id = null;
            this.$errorToast(error);
          });
    },
    editPremises() {
      this.$router.push({name: 'premisesEdit', params: {id: this.premises.id}})
    },
    goToAdvances() {
      this.$router.push({
        name: 'advancePremises',
        params: {premisesId: this.premises.id}
      });
    },
    goToBuilding() {
      this.$router.push({
        name: 'building',
        params: {id: this.premises.buildingId}
      });
    }
  },
  computed: {
    isPremisesFound() {
      return this.premises.id != null;
    },
    isSmall() {
      return this.$vssWidth <= 600;
    },
    menuOptions() {
      return [
        {
          text: this.$_.capitalize(this.$t('advance.advances')),
          onClickFunction: this.goToAdvances
        },
        {
          text: this.$_.capitalize(this.$t('building.building')),
          onClickFunction: this.goToBuilding
        },
      ];
    }
  },
  mounted() {
    this.loadPremises();
  },
  watch: {
    id() {
      this.loadPremises();
    }
  }
}
</script>

<style scoped>

</style>
