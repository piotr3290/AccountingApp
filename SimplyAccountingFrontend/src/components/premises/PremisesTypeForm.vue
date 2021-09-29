<template>
  <v-card class="premises-type-form">

    <template v-if="isFormVisible">

      <v-card-title class="primary">
        {{ $_.capitalize($t('premises.type')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">

        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="premises-type-form" @submit.prevent="handleSubmit(createEditPremisesType)">

            <validation-provider
                :name="$t('premisesType.name')"
                :rules="{required: true, max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="premisesType.name"
                  tag-name="premises-type-name"
                  :counter="64"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('premisesType.name'))"/>
            </validation-provider>

          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            type="submit"
            form="premises-type-form"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>

      </v-card-actions>
    </template>

    <div v-else>
      <h2>{{ $t('premisesType.messages.notFound') }}</h2>
    </div>

  </v-card>
</template>

<script>
import PremisesTypeService from "@/core/service/PremisesTypeService";
import TextInput from "@/components/common/form/TextInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "PremisesTypeForm",
  components: {DefaultButton, TextInput},
  props: ['id'],
  data() {
    return {
      premisesType: {
        id: null,
        name: null
      }
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isPremisesTypeFound() {
      return this.premisesType.id != null;
    },
    isFormVisible() {
      return this.isPremisesTypeFound || this.id == null;
    }
  },
  methods: {
    loadPremisesType() {
      this.$loadingPromises([this.loadPremisesTypeRequest()]);
    },
    loadPremisesTypeRequest() {
      return PremisesTypeService.getPremisesType(this.id)
          .then(response => {
            this.premisesType = response.data;
          })
          .catch(error => {
            this.premisesType.id = null;
            this.$errorToast(error);
          });
    },
    createEditPremisesType() {
      this.$loadingPromises([this.createEditPremisesTypeRequest()]);
    },
    createEditPremisesTypeRequest() {
      if (this.id == null) {
        return PremisesTypeService.createPremisesType(this.premisesType)
            .then(response => {
              this.$successToast(this.$t('premisesType.messages.created'));
              this.$emit('created', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return PremisesTypeService.editPremisesType(this.premisesType)
            .then(response => {
              this.$successToast(this.$t('premisesType.messages.edited'));
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
      this.loadPremisesType();
    }
  },
  watch: {
    id() {
      if (this.id != null) {
        this.loadPremisesType();
      } else {
        this.premisesType.id = null;
      }
    }
  }
}
</script>

<style scoped>

</style>