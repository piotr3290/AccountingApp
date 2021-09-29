<template>
  <v-card class="building-view">
    <v-card-text class="pa-0">
      <v-data-table
          v-if="isBuildingFound"
          :headers="tableHeaders"
          :items="building.premises"
          :no-data-text="$t('building.messages.noPremises')"
          hide-default-footer
          multi-sort
          @click:row="premisesDetails">

        <template #top>
          <v-card-title>
            {{ building.address }}
            <edit-button
                class="mx-2"
                @click="editBuilding"/>
            <v-spacer/>
            <default-button
                :text="building.cooperativeName"
                @click="goToCooperative"/>
          </v-card-title>
          <v-divider/>
        </template>

        <template #footer>

          <v-divider/>

          <div class="text-center">
            <add-button
                class="my-2"
                @click="createPremises"/>
          </div>

        </template>

        <default-button
            @click="createPremises"
            :text="$_.capitalize($t('common.add'))"/>

      </v-data-table>

      <div v-else>
        {{ $t("building.messages.notFound") }}
      </div>

    </v-card-text>
  </v-card>
</template>

<script>
import BuildingService from "@/core/service/BuildingService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import EditButton from "@/components/common/buttons/EditButton";
import AddButton from "@/components/common/buttons/AddButton";

export default {
  name: "BuildingView",
  components: {AddButton, EditButton, DefaultButton},
  props: ['id'],
  data() {
    return {
      building: {
        id: null,
        address: String,
        premises: [],
        cooperativeId: null,
        cooperativeName: null
      }
    }
  },
  methods: {
    loadBuilding() {
      this.$loadingPromises([this.loadBuildingRequest()]);
    },
    loadBuildingRequest() {
      return BuildingService.getBuildingWithPremises(this.id)
          .then(response => {
            this.building = response.data;
          })
          .catch(error => {
            this.building.id = null
            this.$errorToast(error);
          });
    },
    editBuilding() {
      this.$router.push({
        name: 'buildingEdit',
        params: {id: this.id}
      });
    },
    createPremises() {
      this.$router.push({
        name: 'premisesCreate',
        params: {buildingId: this.building.id}
      });
    },
    premisesDetails(premises) {
      this.$router.push({
        name: 'premises',
        params: {id: premises.id}
      });
    },
    goToCooperative() {
      this.$router.push({
        name: 'cooperative',
        params: {id: this.building.cooperativeId}
      });
    }
  },
  computed: {
    tableHeaders() {
      return [
        {
          text: this.$_.capitalize(this.$t('common.premisesNumber')),
          value: 'premisesNumber'
        },
        {
          text: this.$_.capitalize(this.$t('premises.type')),
          value: 'premisesTypeName'
        },
        {
          text: this.$_.capitalize(this.$t('landlord.landlord')),
          value: 'landlordName'
        }
      ];
    },
    isBuildingFound() {
      return this.building.id != null;
    },
  },
  mounted() {
    this.loadBuilding();
  },
  watch: {
    id() {
      this.loadBuilding();
    }
  }
}
</script>

<style scoped>

/deep/ tbody > tr {
  cursor: pointer !important;
}

</style>