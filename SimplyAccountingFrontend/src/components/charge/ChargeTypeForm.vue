<template>
  <div class="charge-type-form">
    <v-card v-if="isFormVisible">
      <v-card-title class="primary">
        {{ $_.capitalize($t('advance.chargeType')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">
        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="charge-type-form" @submit.prevent="handleSubmit(createEditChargeType)">

            <validation-provider
                :name="$t('chargeType.name')"
                :rules="{required: true, max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="chargeType.name"
                  tag-name="charge-type-name"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('chargeType.name'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('chargeType.number')"
                :rules="{required: true, integer: true}"
                v-slot="validationContext">

              <number-input
                  v-model.number="chargeType.number"
                  tag-name="charge-type-number"
                  :step="1"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('chargeType.number'))"/>
            </validation-provider>


          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            type="submit"
            form="charge-type-form"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </v-card>

    <div v-else>
      <h2>{{ $t('chargeType.messages.notFound') }}</h2>
    </div>

  </div>
</template>

<script>
import ChargeTypeService from "@/core/service/ChargeTypeService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import TextInput from "@/components/common/form/TextInput";
import NumberInput from "@/components/common/form/NumberInput";

export default {
  name: "ChargeTypeForm",
  components: {NumberInput, TextInput, DefaultButton},
  props: ['id'],
  data() {
    return {
      chargeType: {
        id: null,
        name: null,
        number: null
      }
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isChargeTypeFound() {
      return this.chargeType.id != null;
    },
    isFormVisible() {
      return this.isChargeTypeFound || this.id == null;
    }
  },
  methods: {
    loadChargeType() {
      this.$loadingPromises([this.loadChargeTypeRequest()]);
    },
    loadChargeTypeRequest() {
      return ChargeTypeService.getChargeType(this.id)
          .then(response => {
            this.chargeType = response.data;
          })
          .catch(error => {
            this.chargeType.id = null;
            this.$errorToast(error);
          });
    },
    createEditChargeType() {
      this.$loadingPromises([this.createEditChargeTypeRequest()]);
    },
    createEditChargeTypeRequest() {
      if (this.id == null) {
        return ChargeTypeService.createChargeType(this.chargeType)
            .then(response => {
              this.$successToast(this.$t('chargeType.messages.created'));
              this.$emit('created', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return ChargeTypeService.editChargeType(this.chargeType)
            .then(response => {
              this.$successToast(this.$t('chargeType.messages.edited'));
              this.$emit('edited', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    cancelForm() {
      this.$emit('cancel');
    }
  },
  mounted() {
    if (this.id != null) {
      this.loadChargeType();
    }
  },
  watch: {
    id() {
      if (this.id != null) {
        this.loadChargeType();
      } else {
        this.chargeType.id = null;
      }
    }
  }
}
</script>

<style scoped>

</style>