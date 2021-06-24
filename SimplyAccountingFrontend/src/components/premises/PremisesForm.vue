<template>
  <v-card class="premises-form">

    <v-card-title class="primary">
      {{ $_.capitalize($t('premises.premises')) }}
      <v-spacer/>
      {{ premises.buildingAddress }}
    </v-card-title>

    <v-card-text class="mt-4 pb-1">
      <div v-if="isFormVisible">

        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="premises-form" @submit.prevent="handleSubmit(createEditPremises)">

            <validation-provider
                :name="$t('common.premisesNumber')"
                :rules="{required: true, max: 8}"
                v-slot="validationContext">

              <text-input
                  v-model="premises.premisesNumber"
                  tag-name="premises-number"
                  :counter="8"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.premisesNumber'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('premises.type')"
                :rules="{required: true}"
                v-slot="validationContext">

              <select-input
                  v-model="premises.premisesTypeId"
                  tag-name="premises-type"
                  :items="premisesTypes"
                  item-text-property="name"
                  item-value-property="id"
                  :label="$_.capitalize($t('premises.type'))"
                  :hint="$t('premises.messages.chooseType')"
                  :errors="validationContext.errors"/>
            </validation-provider>

          </form>
        </validation-observer>
      </div>

      <div v-else-if="!isBuildingFound && buildingId != null">
        <h2>{{ $t('building.messages.notFound') }}</h2>
      </div>

      <div v-else>
        <h2>{{ $t('premises.messages.notFound') }}</h2>
      </div>

    </v-card-text>

    <v-card-actions class="mt-2 mr-2 pb-4">
      <v-spacer/>

      <default-button
          v-if="isFormVisible"
          form="premises-form"
          type="submit"
          :text="createOrEdit"/>

      <default-button
          @click="cancelForm"
          :text="$_.capitalize($t('common.cancel'))"/>

    </v-card-actions>

  </v-card>
</template>

<script>
import BuildingService from "@/core/service/BuildingService";
import PremisesService from "@/core/service/PremisesService";
import PremisesTypeService from "@/core/service/PremisesTypeService";
import TextInput from "@/components/common/form/TextInput";
import SelectInput from "@/components/common/form/SelectInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "PremisesForm",
  components: {DefaultButton, SelectInput, TextInput},
  props: ['id', 'buildingId'],
  data() {
    return {
      premises: {
        id: null,
        premisesNumber: null,
        premisesTypeId: null,
        buildingId: null,
        buildingAddress: null
      },
      premisesTypes: []
    }
  },
  methods: {
    loadBuilding() {
      this.$loadingPromises([this.loadBuildingRequest()]);
    },
    loadBuildingRequest() {
      return BuildingService.getBuilding(this.buildingId)
          .then(response => {
            let building = response.data;
            this.premises.buildingAddress = building.street + ' ' + building.houseNumber + ' ' + building.postalCode;
            this.premises.buildingId = building.id;
          })
          .catch(error => {
            this.premises.buildingId = null;
            this.$errorToast(error);
          });
    },
    loadPremises() {
      this.$loadingPromises([this.loadPremisesRequest()]);
    },
    loadPremisesRequest() {
      return PremisesService.getPremises(this.id)
          .then(response => {
            this.premises = response.data;
          })
          .catch(error => {
            this.premises.id = null;
            this.premises.buildingId = null;
            this.$errorToast(error);
          });
    },
    loadAllPremisesTypes() {
      return PremisesTypeService.getAllPremisesTypes()
          .then(response => {
            this.premisesTypes = response.data;
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    createEditPremises() {
      this.$loadingPromises([this.createEditPremisesRequest()]);
    },
    createEditPremisesRequest() {
      if (this.premises.id == null) {
        return this.createPremises();
      } else {
        return this.editPremises();
      }
    },
    createPremises() {
      return PremisesService.createPremises(this.premises)
          .then(() => {
            this.$successToast(this.$t('premises.messages.created'));
            this.$router.push({name: 'building', params: {id: this.buildingId}});
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    editPremises() {
      return PremisesService.editPremises(this.premises)
          .then(() => {
            this.$successToast(this.$t('premises.messages.edited'));
            this.$router.push({name: 'premises', params: {id: this.id}});
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    cancelForm() {
      if (this.premises.id != null) {
        this.$router.push({name: 'premises', params: {id: this.premises.id}});
      } else if (this.premises.buildingId != null) {
        this.$router.push({name: 'building', params: {id: this.premises.buildingId}});
      } else {
        this.$router.back();
      }
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isBuildingFound() {
      return this.premises.buildingId != null;
    },
    isPremisesFound() {
      return this.premises.id;
    },
    isFormVisible() {
      return this.isBuildingFound || this.isPremisesFound;
    }
  },
  mounted() {
    const promises = [this.loadAllPremisesTypes()];

    if (this.id != null) {
      promises.push(this.loadPremisesRequest());
    } else if (this.buildingId != null) {
      promises.push(this.loadBuildingRequest());
    }

    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      this.loadPremises()
    },
    buildingId() {
      this.loadBuilding()
    }
  }
}
</script>

<style scoped>

</style>