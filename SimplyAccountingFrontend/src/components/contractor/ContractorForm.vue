<template>
  <v-card class="contractor-form">

    <template v-if="isFormVisible">
      <v-card-title class="primary">
        {{ $_.capitalize($t('contractor.contractor')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="contractor-form" @submit.prevent="handleSubmit(createEditContractor)">

            <validation-provider
                :name="$t('contractor.name')"
                :rules="{required: true, min: 3, max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="contractor.name"
                  tag-name="contractor-name"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('contractor.name'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.nip')"
                :rules="{regex: /^[0-9]+$/, max: 10}"
                v-slot="validationContext">

              <text-input
                  v-model="contractor.nip"
                  tag-name="nip"
                  :counter="10"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.nip'))"/>
            </validation-provider>

          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="mt-2 mr-2 pb-4">
        <v-spacer/>
        <default-button
            type="submit"
            form="contractor-form"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>

      </v-card-actions>
    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t('contractor.messages.notFound') }}
      </v-card-title>
    </template>

  </v-card>
</template>

<script>
import ContractorService from "@/core/service/ContractorService";
import TextInput from "@/components/common/form/TextInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "ContractorForm",
  components: {DefaultButton, TextInput},
  props: ['id'],
  data() {
    return {
      contractor: {
        id: null,
        name: null,
        nip: null
      }
    }
  },
  methods: {
    loadContractor() {
      this.$loadingPromises([this.loadContractorRequest()]);
    },
    loadContractorRequest() {
      return ContractorService.getContractor(this.id)
          .then(response => {
            this.contractor = response.data;
          })
          .catch(error => {
            this.contractor.id = null;
            this.$errorToast(error);
          });
    },
    createEditContractor() {
      this.$loadingPromises([this.createEditContractorRequest()]);
    },
    createEditContractorRequest() {
      if (this.contractor.id == null) {
        return ContractorService.createContractor(this.contractor)
            .then(() => {
              this.$successToast(this.$t('contractor.messages.created'));
              this.$router.push({name: 'contractors'});
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return ContractorService.editContractor(this.contractor)
            .then(response => {
              this.$successToast(this.$t('contractor.messages.edited'));
              this.$router.push({name: 'contractor', params: {id: response.data.id}});
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    cancelForm() {
      if (this.contractor.id != null) {
        this.$router.push({name: 'contractor', params: {id: this.contractor.id}});
      } else {
        this.$router.push({name: 'contractors'});
      }
    },
  },
  computed: {
    isFormVisible() {
      return this.id == null || this.contractor.id != null;
    },
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
  },
  mounted() {
    if (this.id != null) {
      this.loadContractor();
    }
  },
  watch: {
    id() {
      this.loadContractor();
    }
  }
}
</script>

<style scoped>

</style>