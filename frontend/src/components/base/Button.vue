<template>
    <div>
        <button class="btn-primary" v-on="$listeners">
            <div class="icon-button"><i class="fas fa-plus"></i></div>
            <div class="text-button">{{text}}</div>
        </button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            
        }
    },
    props: {
        text: String
    },
}
</script>

<style scoped>
    .btn-primary{
        background-color: #3385ff;
        height: 30px;
        display: flex;
        border: none;
        outline: none;
        align-items: center;
        padding: 0 16px;
    }
    .btn-primary .icon-button{
        width: 24px;
        height: 24px;
        display: flex;
        align-items: center;
        color: #fff;
    }
    .btn-primary .text-button{
        font-weight: bold;
        color: #fff;
    }
   
</style>