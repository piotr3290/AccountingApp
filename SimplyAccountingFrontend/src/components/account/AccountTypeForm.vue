<template>
  <div class="account-type-form">
    <v-card v-if="isAccountTypeFound">
      <v-card-title class="primary">
        {{ title }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="account-type-form" @submit.prevent="handleSubmit(editAccountType)">

            <validation-provider
                :name="$t('account.name')"
                :rules="{required: true, max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="accountType.name"
                  tag-name="account-type-name"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('account.name'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('account.number')"
                :rules="{required: true, integer: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="accountType.number"
                  tag-name="account-type-number"
                  :step="1"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('account.number'))"/>
            </validation-provider>


          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            v-if="isAccountTypeFound"
            button-type="submit"
            form="account-type-form"
            :text="$_.capitalize($t('common.save'))"/>

        <default-button
            :text="$_.capitalize($t('common.cancel'))"
            @click="cancelForm"/>
      </v-card-actions>
    </v-card>

    <div v-else>
      <h2>{{ $t('account.messages.notFound') }}</h2>
    </div>

  </div>
</template>

<script>
import AccountTypeService from "@/core/service/AccountTypeService";
import TextInput from "@/components/common/form/TextInput";
import NumberInput from "@/components/common/form/NumberInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "AccountTypeForm",
  components: {DefaultButton, NumberInput, TextInput},
  props: ['id'],
  data() {
    return {
      accountType: {
        id: null,
        name: null,
        number: null
      }
    }
  },
  computed: {
    isAccountTypeFound() {
      return this.accountType.id != null;
    },
    title() {
      let title = '';

      switch (this.id) {
        case 1:
          title = this.$t('cooperative.cooperative');
          break;
        case 2:
          title = this.$t('landlord.landlord');
          break;
        case 3:
          title = this.$t('contractor.contractor');
          break;
        default:
          break;
      }
      return this.$_.capitalize(title + '\'s ' + this.$t('account.type'));
    }
  },
  methods: {
    loadAccountType() {
      this.$loadingPromises([this.loadAccountTypeRequest()]);
    },
    loadAccountTypeRequest() {
      return AccountTypeService.getAccountType(this.id)
          .then(response => {
            this.accountType = response.data;
          })
          .catch(error => {
            this.accountType.id = null;
            this.$errorToast(error);
          });
    },
    editAccountType() {
      this.$loadingPromises([this.editAccountTypeRequest()]);
    },
    editAccountTypeRequest() {
      return AccountTypeService.editAccountType(this.accountType)
          .then(response => {
            this.$successToast(this.$t('account.messages.edited'));
            this.$emit('edited', response.data);
          })
          .catch(error => {
            this.$errorToast(error);
          });
    },
    cancelForm() {
      this.$emit('cancel');
    }
  },
  mounted() {
    this.loadAccountType();
  },
  watch: {
    id() {
      this.loadAccountType();
    }
  }
}
</script>

<style scoped>

</style>