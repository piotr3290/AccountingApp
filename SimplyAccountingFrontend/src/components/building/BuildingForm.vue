<template>
  <v-card class="building-form">

    <v-card-title class="primary">
      {{ $_.capitalize($t('building.building')) }}
      <v-spacer/>
      {{ cooperativeName }}
    </v-card-title>

    <v-card-text class="mt-4 pb-1">
      <div v-if="isFormVisible">

        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="building-form" @submit.prevent="handleSubmit(createEditBuilding)">

            <validation-provider
                :name="$t('common.street')"
                :rules="{required: true, max: 255}"
                v-slot="validationContext">

              <text-input
                  v-model="building.street"
                  tag-name="street"
                  :counter="255"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.street'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.houseNumber')"
                :rules="{required: true, max: 8}"
                v-slot="validationContext">

              <text-input
                  v-model="building.houseNumber"
                  tag-name="house-number"
                  :counter="8"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.houseNumber'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.postalCode')"
                :rules="{required: true, regex: /^[0-9]{2}-[0-9]{3}$/}"
                v-slot="validationContext">

              <text-input
                  v-model="building.postalCode"
                  tag-name="postal-code"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.postalCode'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.city')"
                :rules="{required: true, max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="building.city"
                  tag-name="city"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.city'))"/>
            </validation-provider>

          </form>
        </validation-observer>
      </div>

      <div v-else-if="!isCooperativeFound && cooperativeId != null">
        <h2>{{ $t('cooperative.messages.notFound') }}</h2>
      </div>

      <div v-else>
        <h2>{{ $t('building.messages.notFound') }}</h2>
      </div>
    </v-card-text>

    <v-card-actions class="mt-2 mr-2 pb-4">
      <v-spacer/>

      <default-button
          v-if="isFormVisible"
          type="submit"
          form="building-form"
          :text="createOrEdit"/>

      <default-button
          @click="cancelForm"
          :text="$_.capitalize($t('common.cancel'))"/>
    </v-card-actions>
  </v-card>
</template>

<script>
import BuildingService from "@/core/service/BuildingService";
import CooperativeService from "@/core/service/CooperativeService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import TextInput from "@/components/common/form/TextInput";

export default {
  name: "BuildingForm",
  components: {TextInput, DefaultButton},
  props: ['id', 'cooperativeId'],
  data() {
    return {
      building: {
        id: null,
        street: null,
        houseNumber: null,
        postalCode: null,
        city: null,
        cooperativeId: null
      },
      cooperativeName: null
    }
  },
  methods: {
    createEditBuilding() {
      this.$loadingPromises([this.createEditBuildingRequest()]);
    },
    createEditBuildingRequest() {
      if (this.id == null) {
        return BuildingService.createBuilding(this.building)
            .then(() => {
              this.$successToast(this.$t('building.messages.created'));
              this.$router.push({name: 'cooperative', params: {id: this.cooperativeId}});
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return BuildingService.editBuilding(this.building)
            .then(() => {
              this.$successToast(this.$t('building.messages.edited'));
              this.$router.push({name: 'building', params: {id: this.id}});
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadCooperative() {
      this.$loadingPromises([this.loadCooperativeRequest()]);
    },
    loadCooperativeRequest() {
      return CooperativeService.getCooperative(this.cooperativeId)
          .then(response => {
            this.building.cooperativeId = response.data.id;
            this.cooperativeName = response.data.name;
          })
          .catch(error => {
            this.building.cooperativeId = null;
            this.cooperativeName = null;
            this.$errorToast(error);
          });
    },
    loadBuilding() {
      this.$loadingPromises([this.loadBuildingRequest()]);
    },
    loadBuildingRequest() {
      return BuildingService.getBuilding(this.id)
          .then(response => {
            this.building = response.data;
          })
          .catch(error => {
            this.building.id = null;
            this.building.cooperativeId = null;
            this.$errorToast(error);
          });
    },
    cancelForm() {
      if (this.building.id != null) {
        this.$router.push({
          name: 'building',
          params: {id: this.building.id}
        });
      } else if (this.building.cooperativeId != null) {
        this.$router.push({
          name: 'cooperative',
          params: {id: this.building.cooperativeId}
        });
      } else {
        this.$router.back();
      }
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isFormVisible() {
      return this.isBuildingFound || this.isCooperativeFound;
    },
    isBuildingFound() {
      return this.building.id != null;
    },
    isCooperativeFound() {
      return this.building.cooperativeId != null;
    }
  },
  mounted() {
    if (this.cooperativeId != null) {
      this.loadCooperative();
    }
    if (this.id != null) {
      this.loadBuilding();
    }

  },
  watch: {
    cooperativeId() {
      this.loadCooperative();
    },
    id() {
      this.loadBuilding();
    }
  }
}
</script>

<style scoped>

</style>