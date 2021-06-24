<template>
  <v-card class="cooperative-form">

    <v-card-title class="primary">
      {{ $_.capitalize($t('cooperative.cooperative')) }}
    </v-card-title>

    <v-card-text class="mt-4 pb-1">
      <div v-if="isFormVisible">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="cooperative-form" @submit.prevent="handleSubmit(createEditCooperative)">

            <validation-provider
                :name="$t('cooperative.name')"
                :rules="{required: true, min: 3, max: 255}"
                v-slot="validationContext">

              <text-input
                  v-model="cooperative.name"
                  tag-name="cooperative-name"
                  :counter="255"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('cooperative.name'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.nip')"
                :rules="{regex: /^[0-9]+$/, max: 10}"
                v-slot="validationContext">

              <text-input
                  v-model="cooperative.nip"
                  tag-name="nip"
                  :counter="10"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.nip'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('cooperative.regon')"
                :rules="{regex: /^[0-9]+$/, max: 14}"
                v-slot="validationContext">

              <text-input
                  v-model="cooperative.regon"
                  tag-name="regon"
                  :counter="14"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('cooperative.regon'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.street')"
                :rules="{max: 255}"
                v-slot="validationContext">

              <text-input
                  v-model="cooperative.street"
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
                  v-model="cooperative.houseNumber"
                  tag-name="house-number"
                  :counter="8"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.houseNumber'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.postalCode')"
                :rules="{regex: /^[0-9]{2}-[0-9]{3}$/}"
                v-slot="validationContext">

              <text-input
                  v-model="cooperative.postalCode"
                  tag-name="postal-code"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.postalCode'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.city')"
                :rules="{max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="cooperative.city"
                  tag-name="city"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.city'))"/>
            </validation-provider>

          </form>
        </validation-observer>
      </div>

      <div v-else>
        {{ $t('cooperative.messages.notFound') }}
      </div>
    </v-card-text>

    <v-card-actions class="mt-2 mr-2 pb-4">
      <v-spacer/>

      <default-button
          v-if="isFormVisible"
          form="cooperative-form"
          type="submit"
          :text="createOrEdit"/>

      <default-button
          @click="cancelForm"
          :text="$_.capitalize($t('common.cancel'))"/>
    </v-card-actions>

  </v-card>
</template>

<script>
import CooperativeService from "@/core/service/CooperativeService";
import TextInput from "@/components/common/form/TextInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "CooperativeForm",
  components: {DefaultButton, TextInput},
  props: ['id'],
  data() {
    return {
      cooperative: {
        id: null,
        name: null,
        nip: null,
        regon: null,
        city: null,
        street: null,
        houseNumber: null,
        postalCode: null
      }
    }
  },
  mounted() {
    if (this.id != null) {
      this.loadCooperative();
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isFormVisible() {
      return this.id == null || this.cooperative.id != null;
    }
  },
  methods: {
    createEditCooperative() {
      this.$loadingPromises([this.createEditCooperativeRequest()]);
    },
    createEditCooperativeRequest() {
      if (this.id == null) {
        return CooperativeService.createCooperative(this.cooperative)
            .then(() => {
              this.$successToast(this.$t('cooperative.messages.created'));
              this.$router.push({name: 'cooperatives'});
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return CooperativeService.editCooperative(this.cooperative)
            .then(response => {
              this.$successToast(this.$t('cooperative.messages.edited'));
              this.$router.push({name: 'cooperative', params: {id: response.data.id}});
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
      return CooperativeService.getCooperative(this.id)
          .then(response => {
            this.cooperative = response.data;
          })
          .catch(error => {
            this.cooperative.id = null;
            this.$errorToast(error);
          });
    },
    cancelForm() {
      if (this.cooperative.id != null) {
        this.$router.push({name: 'cooperative', params: {id: this.cooperative.id}});
      } else {
        this.$router.push({name: 'cooperatives'});
      }
    }
  },
  watch: {
    id() {
      this.loadCooperative();
    }
  }
}
</script>

<style scoped>

</style>