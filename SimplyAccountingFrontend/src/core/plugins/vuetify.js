import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import colors from '@/scss/_colors.scss'
import AppIcon from "@/components/common/AppIcon";

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
            dark: {
                primary: colors.primaryDark,
                accent: colors.accentDark,
                secondary: colors.secondaryDark,
                success: colors.successDark,
                info: colors.infoDark,
                warning: colors.warningDark,
                error: colors.errorDark,
            },
            light: {
                primary: colors.primaryLight,
                accent: colors.accentLight,
                secondary: colors.secondaryLight,
                success: colors.successLight,
                info: colors.infoLight,
                warning: colors.warningLight,
                error: colors.errorLight,
            }
        },
    },
    icons: {
        values: {
            appIcon: {
                component: AppIcon
            }
        }
    }
});
