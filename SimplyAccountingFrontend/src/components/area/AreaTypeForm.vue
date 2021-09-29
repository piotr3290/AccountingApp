<template>
  <v-card class="area-type-form">

    <template v-if="isFormVisible">

      <v-card-title class="primary">
        {{ $_.capitalize($t('area.type')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">

        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="area-type-form" @submit.prevent="handleSubmit(createEditAreaType)">

            <validation-provider
                :name="$t('areaType.name')"
                :rules="{required: true, max: 64}"
                v-slot="validationContext">

              <text-input
                  v-model="areaType.name"
                  tag-name="area-type-name"
                  :counter="64"
                  :clearable="true"
                  :errors="validationContext.errors"/>
            </validation-provider>

          </form>
        </validation-observer>

      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            type="submit"
            form="area-type-form"
            :text="createOrEdit"/>
        
        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>

    </template>

    <template v-else>
      <v-card-title class="error">
        {{ $t('areaType.messages.notFound') }}
      </v-card-title>
    </template>

  </v-card>
</template>

<script>
import AreaTypeService from "@/core/service/AreaTypeService";
import TextInput from "@/components/common/form/TextInput";
import DefaultButton from "@/components/common/buttons/DefaultButton";

export default {
  name: "AreaTypeForm",
  components: {DefaultButton, TextInput},
  props: ['id'],
  data() {
    return {
      areaType: {
        id: null,
        name: null
      }
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isAreaTypeFound() {
      return this.areaType.id != null;
    },
    isFormVisible() {
      return this.isAreaTypeFound || this.id == null;
    }
  },
  methods: {
    loadAreaType() {
      this.$loadingPromises([this.loadAreaTypeRequest()]);
    },
    loadAreaTypeRequest() {
      return AreaTypeService.getAreaType(this.id)
          .then(response => {
            this.areaType = response.data;
          })
          .catch(error => {
            this.areaType.id = null;
            this.$errorToast(error);
          });
    },
    createEditAreaType() {
      this.$loadingPromises([this.createEditAreaTypeRequest()]);
    },
    createEditAreaTypeRequest() {
      if (this.id == null) {
        return AreaTypeService.createAreaType(this.areaType)
            .then(response => {
              this.$successToast(this.$t('areaType.messages.created'));
              this.$emit('created', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return AreaTypeService.editAreaType(this.areaType)
            .then(response => {
              this.$successToast(this.$t('areaType.messages.edited'));
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
      this.loadAreaType();
    }
  },
  watch: {
    id() {
      if (this.id != null) {
        this.loadAreaType();
      } else {
        this.areaType.id = null;
      }
    }
  }
}
</script>

<style scoped>

</style>