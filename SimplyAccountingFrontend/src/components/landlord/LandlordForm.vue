<template>
  <v-card class="landlord-form">

    <template v-if="isFormVisible">
      <v-card-title class="primary">
        {{ $_.capitalize($t('landlord.landlord')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="landlord-form" @submit.prevent="handleSubmit(createEditLandlord)">

            <validation-provider
                :name="$t('common.firstName')"
                :rules="{required: true, max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="landlord.firstName"
                  tag-name="landlord-name"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.firstName'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.lastName')"
                :rules="{required: true, max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="landlord.lastName"
                  tag-name="landlord-last-name"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.lastName'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.emailAddress')"
                :rules="{max: 255}"
                v-slot="validationContext">

              <text-input
                  v-model="landlord.emailAddress"
                  tag-name="email"
                  :counter="255"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.emailAddress'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.phoneNumber')"
                :rules="{max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="landlord.phoneNumber"
                  tag-name="phone-number"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.phoneNumber'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.street')"
                :rules="{max: 255}"
                v-slot="validationContext">

              <text-input
                  v-model="landlord.street"
                  tag-name="street"
                  :counter="255"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.street'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.houseNumber')"
                :rules="{max: 8}"
                v-slot="validationContext">

              <text-input
                  v-model="landlord.houseNumber"
                  tag-name="house-number"
                  :counter="8"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.houseNumber'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.premisesNumber')"
                :rules="{max: 8}"
                v-slot="validationContext">

              <text-input
                  v-model="landlord.premisesNumber"
                  tag-name="premises-number"
                  :counter="8"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.premisesNumber'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.postalCode')"
                :rules="{regex: /^[0-9]{2}-[0-9]{3}$/}"
                v-slot="validationContext">

              <text-input
                  v-model="landlord.postalCode"
                  tag-name="postal-code"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.postalCode'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.city')"
                :rules="{max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="landlord.city"
                  tag-name="city"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.city'))"/>
            </validation-provider>

          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="mt-2 mr-2 pb-4">
        <v-spacer/>
        <default-button
            type="submit"
            form="landlord-form"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t('landlord.messages.notFound') }}
      </v-card-title>
    </template>

  </v-card>
</template>

<script>
import LandlordService from "@/core/service/LandlordService";
import TextInput from "@/components/common/form/TextInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "LandlordForm",
  components: {DefaultButton, TextInput},
  props: ['id'],
  data() {
    return {
      landlord: {
        id: null,
        firstName: null,
        middleName: null,
        lastName: null,
        emailAddress: null,
        phoneNumber: null,
        street: null,
        houseNumber: null,
        premisesNumber: null,
        postalCode: null,
        city: null
      }
    }
  },
  methods: {
    loadLandlord() {
      this.$loadingPromises([this.loadLandlordRequest()]);
    },
    loadLandlordRequest() {
      return LandlordService.getLandlord(this.id)
          .then(response => {
            this.landlord = response.data;
          })
          .catch(error => {
            this.landlord.id = null;
            this.$errorToast(error);
          });
    },
    createEditLandlord() {
      this.$loadingPromises([this.createEditLandlordRequest()]);
    },
    createEditLandlordRequest() {
      if (this.id == null) {
        return LandlordService.createLandlord(this.landlord)
            .then(() => {
              this.$successToast(this.$t('landlord.messages.created'));
              this.$router.push({name: 'landlords'});
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return LandlordService.editLandlord(this.landlord)
            .then(response => {
              this.$successToast(this.$t('landlord.messages.edited'));
              this.$router.push({
                name: 'landlord',
                params: {id: response.data.id}
              });
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    cancelForm() {
      if (this.landlord.id != null) {
        this.$router.push({
          name: 'landlord',
          params: {id: this.landlord.id}
        });
      } else {
        this.$router.push({
          name: 'landlords'
        });
      }
    }
  },
  computed: {
    isFormVisible() {
      return this.id == null || this.landlord.id != null;
    },
    createOrEdit() {
      return this.id != null
          ? this.$_.capitalize(this.$t('common.edit'))
          : this.$_.capitalize(this.$t('common.create'));
    },
  },
  mounted() {
    if (this.id != null) {
      this.loadLandlord();
    }
  },
  watch: {
    id() {
      this.loadLandlord();
    }
  }
}
</script>

<style scoped>

</style>