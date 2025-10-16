<template>
    <ElForm
        ref="form"
        :model="model"
        @submit="save"
        :rules="rules"
        label-width="60"
        label-position="left"
    >
        <ElFormItem prop="name" label="科室">
            <ElInput v-model="model.name" type="text" placeholder="名称" />
        </ElFormItem>
        <ElFormItem prop="description" label="描述">
            <ElInput v-model="model.description" type="text" placeholder="说明" />
        </ElFormItem>
        <ElFormItem label="">
            <ElButton type="primary" native-type="submit">保存</ElButton>
        </ElFormItem>
    </ElForm>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from 'element-plus'
import request from '@/utils/request'

const route = useRoute()
const router = useRouter()

const form = ref(null)

const model = reactive({
    name: '',
    description: '',
})

const rules = reactive({
    name: [
        { required: true, message: '请输入名称。', trigger: 'blur' },
        { min: 2, max: 32, message: '名称仅限2~32个字符。', trigger: 'change' },
    ],
    description: [
        { required: true, message: '请输入描述。', trigger: 'blur' },
        { min: 2, max: 64, message: '名称仅限2~64个字符。', trigger: 'change' },
    ],
})

const fetch = (id) => {
    request.get(`/office/${id}`).then((response) => {
        if (response.data.status === true) {
            Object.assign(model, response.data.payload.office)
        }
    })
}

fetch(route.params.id)

const save = (e) => {
    e.preventDefault()
    form.value
        .validate()
        .then((result) => {
            if (result === true) {
                request.post('/office/update', { ...model }).then((response) => {
                    if (response.data.status === true) {
                        ElMessage.success('保存成功！')
                        router.push({ name: 'office_index' })
                    } else {
                        ElMessage.error('保存失败！')
                    }
                })
            }
        })
        .catch(() => {
            ElMessage.error('保存失败！')
        })
}
</script>
