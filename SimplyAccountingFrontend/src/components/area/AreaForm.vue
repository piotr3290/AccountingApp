<template>
  <v-card class="area-form">

    <template v-if="isFormVisible">

      <v-card-title class="primary">
        {{ $_.capitalize($t('area.surface')) }}
      </v-card-title>

      <v-card-text class="mt-4 pb-1">

        <validation-observer ref="observer" v-slot="{handleSubmit}">
          <form id="area-form" @submit.prevent="handleSubmit(createEditArea)">

            <validation-provider
                :name="$t('area.surface')"
                :rules="{required: true, min_value: 0}"
                v-slot="validationContext">

              <number-input
                  v-model.number="area.value"
                  tag-name="area-surface"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('area.surface'))"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.from')"
                v-slot="validationContext">

              <date-input
                  v-model="area.startDate"
                  tag-name="start-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.from'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('common.to')"
                v-slot="validationContext">

              <date-input
                  v-model="area.endDate"
                  tag-name="end-date"
                  :errors="validationContext.errors"
                  :label="$_.capitalize($t('common.to'))"
                  :clearable="true"/>
            </validation-provider>

            <validation-provider
                :name="$t('area.type')"
                :rules="{required: true}"
                v-slot="validationContext">

              <select-input
                  v-model="area.areaTypeId"
                  tag-name="area-type"
                  :items="areaTypes"
                  item-text-property="name"
                  item-value-property="id"
                  :label="$_.capitalize($t('area.type'))"
                  :hint="$t('area.messages.chooseType')"
                  :disabled="area.id!=null"
                  :errors="validationContext.errors"/>
            </validation-provider>


          </form>
        </validation-observer>
      </v-card-text>

      <v-card-actions class="pb-4">
        <v-spacer/>

        <default-button
            form="area-form"
            type="submit"
            :text="createOrEdit"/>

        <default-button
            @click="cancelForm"
            :text="$_.capitalize($t('common.cancel'))"/>
      </v-card-actions>
    </template>

    <template v-else-if="id != null">
      <v-card-title class="error">
        {{ $t('area.messages.notFound') }}
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
        {{ $t('area.messages.noPremises') }}
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
import AreaService from "@/core/service/AreaService";
import AreaTypeService from "@/core/service/AreaTypeService";
import DefaultButton from "@/components/common/buttons/DefaultButton";
import NumberInput from "@/components/common/form/NumberInput";
import DateInput from "@/components/common/form/DateInput";
import SelectInput from "@/components/common/form/SelectInput";

export default {
  name: "AreaForm",
  components: {SelectInput, NumberInput, DefaultButton, DateInput},
  props: ['id', 'premisesId'],
  data() {
    return {
      area: {
        id: null,
        startDate: null,
        endDate: null,
        value: null,
        premisesId: null,
        areaTypeId: null
      },
      areaTypes: []
    }
  },
  methods: {
    cancelForm() {
      this.$emit('cancel');
    },
    createEditArea() {
      this.$loadingPromises([this.createEditAreaRequest()]);
    },
    createEditAreaRequest() {
      if (this.area.id == null) {
        return AreaService.createArea(this.area)
            .then(response => {
              this.$successToast(this.$t('area.messages.created'));
              this.$emit('updateAreas', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      } else {
        return AreaService.editArea(this.area)
            .then(response => {
              this.$successToast(this.$t('area.messages.edited'));
              this.$emit('updateAreas', response.data);
            })
            .catch(error => {
              this.$errorToast(error);
            });
      }
    },
    loadArea() {
      this.$loadingPromises([this.loadAreaRequest()]);
    },
    loadAreaRequest() {
      return AreaService.getArea(this.id)
          .then(response => {
            this.area = response.data;
          })
          .catch(error => {
            this.area.id = null;
            this.$errorToast(error);
          });
    },
    loadAllAreaTypes() {
      return AreaTypeService.getAllAreaTypes()
          .then(response => {
            this.areaTypes = response.data;
          })
          .catch(error => {
            this.areaTypes = [];
            this.$errorToast(error);
          });
    }
  },
  computed: {
    createOrEdit() {
      return this.id != null ? this.$_.capitalize(this.$t('common.edit')) : this.$_.capitalize(this.$t('common.create'));
    },
    isAreaFound() {
      return this.area.id != null;
    },
    isFormVisible() {
      return this.isAreaFound || this.premisesId != null;
    }
  },
  mounted() {
    const promises = [this.loadAllAreaTypes()];

    if (this.id != null) {
      promises.push(this.loadAreaRequest());
    } else {
      this.area.premisesId = this.premisesId;
    }

    this.$loadingPromises(promises);
  },
  watch: {
    id() {
      if (this.id != null || this.premisesId == null) {
        this.loadArea();
      } else {
        this.area.id = null;
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