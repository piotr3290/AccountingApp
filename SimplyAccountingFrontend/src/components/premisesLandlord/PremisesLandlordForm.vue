<template>
  <v-card class="premises-landlord-form">

    <template v-if="isFormVisible">

      <v-card-title class="primary">
        {{ $_.capitalize($t('landlord.landlord')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="premises-landlord-form" @submit.prevent="handleSubmit(createEditPremisesLandlord)">

            <validation-provider
                :name="$t('common.from')"
                v-slot="validationContext">

              <date-input
                  v-model="premisesLandlord.startDate"
                  tag-name="start-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.from'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.to')"
                v-slot="validationContext">

              <date-input
                  v-model="premisesLandlord.endDate"
                  tag-name="end-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.to'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('landlord.landlord')"
                :rules="{required: true}"
                v-slot="validationContext">

              <autocomplete-input
                  v-model="premisesLandlord.landlordId"
                  tag-name="landlord"
                  :items="landlords"
                  item-text-property="fullName"
                  item-value-property="id"
                  :label="$_.capitalize($t('landlord.landlord'))"
                  :hint="$t('premisesLandlord.messages.chooseLandlord')"
                  :disabled="premisesLandlord.id != null"
                  :errors="validationContext.errors"
                  :clearable="true"/>
            </validation-provider>


          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            type="submit"
            form="premises-landlord-form"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>

    </template>

    <template v-else-if="id != null">
      <v-card-title class="error">
        {{ $t('premisesLandlord.messages.notFound') }}
      </v-card-title>

      <v-card-actions class="pb-4">
        <v-spacer/>
        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t('premisesLandlord.messages.noPremises') }}
      </v-card-title>

      <v-card-actions class="pb-4">
        <v-spacer/>
        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </template>

  </v-card>
</template>

<script>
import PremisesLandlordService from "@/core/service/PremisesLandlordService";
import LandlordService from "@/core/service/LandlordService";
import DateInput from "@/components/common/form/DateInput";
import AutocompleteInput from "@/components/common/form/AutocompleteInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "PremisesLandlordForm",
  components: {DefaultButton, AutocompleteInput, DateInput},
  props: ['id', 'premisesId'],
  data() {
    return {
      premisesLandlord: {
        id: null,
        startDate: null,
        endDate: null,
        premisesId: null,
        landlordId: null
      },
      landlords: []
    }
  },
  methods: {
    cancelForm() {
      this.$emit('cancel');
    },
    createEditPremisesLandlord() {
      this.$loadingPromises([this.createEditPremisesLandlordRequest()]);
    },
    createEditPremisesLandlordRequest() {
      if (this.premisesLandlord.id == null) {
        return PremisesLandlordService.createPremisesLandlord(this.premisesLandlord)
            .then(response => {
              this.$successToast(this.$t('premisesLandlord.messages.created'));
              this.$emit('created', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return PremisesLandlordService.editPremisesLandlord(this.premisesLandlord)
            .then(response => {
              this.$successToast(this.$t('premisesLandlord.messages.edited'));
              this.$emit('edited', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadPremisesLandlord() {
      this.$loadingPromises([this.loadPremisesLandlordRequest()]);
    },
    loadPremisesLandlordRequest() {
      return PremisesLandlordService.getPremisesLandlord(this.id)
          .then(response => {
            this.premisesLandlord = response.data;
          })
          .catch(error => {
            this.premisesLandlord.id = null;
            this.$errorToast(error);
          });
    },
    loadAllLandlords() {
      this.$loadingPromises([this.loadAllLandlordsRequest()]);
    },
    loadAllLandlordsRequest() {
      return LandlordService.getAllLandlords()
          .then(response => {
            this.landlords = this.$_.sortBy(response.data, landlord => {
              return landlord.fullName;
            });
          })
          .catch(error => {
            this.landlords = [];
            this.$errorToast(error);
          })
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isPremisesLandlordFound() {
      return this.premisesLandlord.id != null;
    },
    isFormVisible() {
      return this.isPremisesLandlordFound || this.premisesId != null;
    }
  },
  mounted() {
    const promises = [this.loadAllLandlordsRequest()];

    if (this.id != null) {
      promises.push(this.loadPremisesLandlordRequest());
    } else {
      this.premisesLandlord.premisesId = this.premisesId;
    }

    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      if (this.id != null || this.premisesId == null) {
        this.loadPremisesLandlord();
      } else {
        this.premisesLandlord.id = null;
      }
    },
    premisesId() {
      this.cancelForm();
    }
  }
}
</script>

<style scoped>

</style>